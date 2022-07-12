import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

class ImageSanitizerTest {

    @Test
    void randomizeImageBytes() throws IOException {

        Path imageFile = Paths.get("src","test","resources", "profilbild.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile);

        byte[] randomized = ImageSanitizer.randomizeImageBytes(imageBytes);

        ByteArrayInputStream is2 = new ByteArrayInputStream(randomized);
        BufferedImage imageRandomized = ImageIO.read(is2);

        ImageIO.write(imageRandomized, "JPG", new File("src/test/resources/profilbild1.jpg"));

        assertNotNull(imageRandomized);
    }

    @Test
    void convertBMPthenJPG() throws IOException {

        Path imageFile = Paths.get("src","test","resources", "profilbild.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile);

        assertNotNull(imageBytes);

        byte[] converted = ImageSanitizer.convertToBMPthenJPG(imageBytes);

        assertNotNull(converted);

        ByteArrayInputStream is = new ByteArrayInputStream(converted);
        BufferedImage imageConverted = ImageIO.read(is);

        assertNotNull(imageConverted);

        //ImageIO.write(imageConverted, "PNG", new File("src/test/resources/profilbild.png"));
    }

    @Test
    void convertFormat() throws IOException {

        Path imageFile = Paths.get("src","test","resources", "profilbild.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile);

        ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(is);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ImageSanitizer.convertFormat(image, os, "PNG");

        ByteArrayInputStream is2 = new ByteArrayInputStream(os.toByteArray());
        BufferedImage imageConverted = ImageIO.read(is2);

        //ImageIO.write(imageConverted, "PNG", new File("src/test/resources/profilbild3.png"));

        assertNotNull(imageConverted);
    }

    @Test
    void randomizeAndConvert() throws IOException {
        Path imageFile = Paths.get("src","test","resources", "profilbild.jpg");
        byte[] imageBytes = Files.readAllBytes(imageFile);

        byte[] randomized = ImageSanitizer.randomizeImageBytes(imageBytes);

        ByteArrayInputStream is = new ByteArrayInputStream(randomized);
        //BufferedImage imageRandomized = ImageIO.read(is);

        byte[] converted = ImageSanitizer.convertToBMPthenJPG(is.readAllBytes());

        assertNotNull(converted);

        ByteArrayInputStream is2 = new ByteArrayInputStream(converted);
        BufferedImage imageConverted = ImageIO.read(is2);

        assertNotNull(imageConverted);

        //ImageIO.write(imageConverted, "PNG", new File("src/test/resources/profilbild2.png"));
    }
}
