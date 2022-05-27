package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.Test;

import plagdetect.IPlagiarismDetector;
import plagdetect.PlagiarismDetector;

public class TestTinyDocs
{
    public static final String TINYDOCS = "docs/tinydocs";
    private static final String FILE1 = "file1.txt";
    private static final String FILE2 = "file2.txt";
    private static final String FILE3 = "file3.txt";
    
    private IPlagiarismDetector detector;
    
    public void makeDetector(int n) throws Exception {
        // n is the size of an n-gram
        detector = new PlagiarismDetector(n); //initialize thingies
        detector.readFilesInDirectory(new File(TINYDOCS)); //processes all files (in tinydocs)
    }

    @Test
    public void testConstructorAndGetN() throws Exception {
        makeDetector(3);
        assertEquals(3, detector.getN());
    }
    
    @Test
    public void testFile1N3() throws Exception {
        makeDetector(3);
        assertEquals(7, detector.getNumNgramsInFile(FILE1));
        Collection<String> ngrams = detector.getNgramsInFile(FILE1);
        assertTrue(ngrams.contains("the quick brown"));
        assertTrue(ngrams.contains("quick brown fox"));
        assertTrue(ngrams.contains("brown fox jumps"));
        assertTrue(ngrams.contains("fox jumps over")); 
        assertTrue(ngrams.contains("jumps over the")); 
        assertTrue(ngrams.contains("over the lazy")); 
        assertTrue(ngrams.contains("the lazy dog")); 
    }
    
    @Test
    public void testFile1N5() throws Exception {
        makeDetector(5);
        assertEquals(5, detector.getNumNgramsInFile(FILE1));
        Collection<String> ngrams = detector.getNgramsInFile(FILE1);
        assertTrue(ngrams.contains("the quick brown fox jumps"));
        assertTrue(ngrams.contains("quick brown fox jumps over"));
        assertTrue(ngrams.contains("brown fox jumps over the"));
        assertTrue(ngrams.contains("fox jumps over the lazy"));
        assertTrue(ngrams.contains("jumps over the lazy dog"));
    }

    @Test
    public void testFile1File2InCommon() throws Exception {
        makeDetector(3);
        String msg1 = "If you return 6, then you are double-counting";
        assertEquals(msg1, 3, detector.getNumNGramsInCommon(FILE1, FILE2));
        String msg2 = "If you return 0, then you are counting in one direction (file1 to file2) but not the reverse (file2 to file1)";
        assertEquals(msg2, 3, detector.getNumNGramsInCommon(FILE2, FILE1));
    }
    
    @Test
    public void testFile1File2InCommonNoCrash() throws Exception {
        makeDetector(3);
        String msg = "Two files with no n-grams in common should return 0";
        assertEquals(msg, 0, detector.getNumNGramsInCommon(FILE1, FILE3));
        assertEquals(msg, 0, detector.getNumNGramsInCommon(FILE3, FILE1));
    }
}
