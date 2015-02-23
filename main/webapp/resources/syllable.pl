#!/usr/bin/perl
use Getopt::Std;
use Math::Complex;

getopts('i:o:');

my $vowels = "AEIOUY";
my $nonE = "AIOUY";

open(MYINPUTFILE, $opt_i);

my @lines = <MYINPUTFILE>;
my $nameList = $lines[0];

# First syllable: All letters up through consonants AFTER first vowels
# e.g. [MICH]AEL , [NICH]OLAS , [BENJ]AMIN , [AM]ANDA

# Later syllables are any vowels followed by consonants
# e.g. MEL[ISS]A , LYS[AND]ER , SCHOF[IELD]

# Last syllable can include a single E at the end

my @beg = $nameList =~ /,([^,$vowels]*[$vowels]+[^,$vowels]*)/g;
my @mid = $nameList =~ /,[$vowels]+[^,$vowels]+([$vowels]+[^,$vowels]+)[$vowels]+/g;
my @end = $nameList =~ /[^,$vowels]+([$vowels]*[^,$vowels]*[E]?),/g;

my @names = split(',', $nameList);
# Find standard deviation and mean
my $meanTotal = 0, $total = 0;
for my $name (@names){
	my $syl =()= $name =~ /[$vowels]+/gi;
	$total += $syl;
	$meanTotal += $syl * $syl;
}
my $standardDeviation = sqrt($meanTotal / $#names);

print "\nStandard Deviation: $standardDeviation\nMean: " . $total / $#names . "\n";

open(MYOUTPUTFILE, ">", $opt_o);

print MYOUTPUTFILE "\nStandard Deviation: $standardDeviation\nMean: " . $total / $#names . "\n";

sub getParts{
    my @list = @_;
    my %hash = ();
    foreach(@list){
        if(!$hash{$_}){
            $hash{$_} = 1;
        } else {
            $hash{$_} = $hash{$_} + 1;
        }
    }
    my $n = 1;
    my $total = $#list;
    # keep track of ratio of empty to total and adjust afterwards
    my $blankRatio = $hash{''}/$total;
    while(%hash > 100){
        foreach(keys %hash){
            if($hash{$_} < $n){
                delete $hash{$_};
                $total -= $n;
            }
        }
        $n++;
    }
    my $newBlank = int(.5 + $hash{''} * $blankRatio);
    $total = $total - $hash{''} - $newBlank;
    if($newBlank){
        $hash{''} = $newBlank;
    }

    foreach(sort keys %hash){
        print MYOUTPUTFILE "\"$_\":$hash{$_}, ";
    }
    print MYOUTPUTFILE "\n\n";
}

getParts(sort @beg);
getParts(sort @mid);
getParts(sort @end);

close MYOUTPUTFILE;