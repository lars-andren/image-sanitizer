import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class ImageSanitizer {

    private static Random random = new Random();

    public static byte[] randomizeImageBytes(byte[] imageFile) throws IOException {

        ByteArrayInputStream is = new ByteArrayInputStream(imageFile);
        BufferedImage image = ImageIO.read(is);

        int maxRGB = -10;
        int minRGB = 10000;

        for (int y = 0; y < image.getHeight(null); y++) {
            for (int x = 0; x < image.getWidth(null); x++) {
                int color = image.getRGB(x, y);
                int rand = random.nextInt((3 - 1) + 1) + 1;

                if (color > maxRGB) {
                    maxRGB = color;
                }

                if (color < minRGB) {
                    minRGB = color;
                }

                int newColor = randomModification(color, rand);
                image.setRGB(x, y, newColor);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean result = ImageIO.write(image, "JPG", outputStream);

        if (!result) {
            throw new IOException("Could not write image to outputstream, possibly incorrect format.");
        }

        System.out.println("Max: " + maxRGB);
        System.out.println("Min: " + minRGB);

        return outputStream.toByteArray();
    }

    private static int randomModification(int input, int rand) {

        int correctedInput = handleBlackOrWhite(input);

        if (rand == 1) {
            return correctedInput + 1;
        } else if (rand == 2) {
            return correctedInput - 1;
        } else {
            return correctedInput;
        }
    }

    private static int handleBlackOrWhite(int input) {

        if (-1 <= input &&  input <= 1) {
            return -2;
        }
        else if (-16777216 <= input && input >= 16777215) {
            return -2;
        } else if (input >= 16777215 ) {
            return 1677210;
        }
        else {
            return input;
        }
    }

    public static byte[] convertToBMPthenJPG(byte[] imageFile) throws IOException {

        ByteArrayInputStream is = new ByteArrayInputStream(imageFile);
        BufferedImage originalImage = ImageIO.read(is);

        ByteArrayOutputStream pbmOutStream = new ByteArrayOutputStream();

        boolean pbmConversionSuccess = convertFormat(originalImage, pbmOutStream, "BMP");

        if (!pbmConversionSuccess) {
            throw new IOException("Could not convert file!");
        }

        ByteArrayInputStream pbmStream = new ByteArrayInputStream(pbmOutStream.toByteArray());
        BufferedImage pbmImage = ImageIO.read(pbmStream);

        ByteArrayOutputStream pngOutStream = new ByteArrayOutputStream();

        boolean pngConversionSuccess = convertFormat(pbmImage, pngOutStream, "JPG");

        if (!pngConversionSuccess) {
            throw new IOException("Could not convert file!");
        }

        return pngOutStream.toByteArray();
    }

    private static byte[] convertImage(byte[] imageFile, String format) throws IOException {
        ByteArrayOutputStream convertedStream = new ByteArrayOutputStream(imageFile.length);
        ByteArrayInputStream is = new ByteArrayInputStream(imageFile);

        BufferedImage originalImage = ImageIO.read(is);

        convertFormat(originalImage, convertedStream, format);

        return convertedStream.toByteArray();
    }

    public static boolean convertFormat(BufferedImage inputImage, ByteArrayOutputStream convertedStream, String formatName) throws IOException {

        boolean result = ImageIO.write(inputImage, formatName, convertedStream);
        //convertedStream.close();

        return result;
    }
}