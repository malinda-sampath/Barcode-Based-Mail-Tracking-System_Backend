package com.UoR_MTS_Backend.mail_tracking_system.utill.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BarcodeImageGenerator {

    public static byte[] generateBarcode(String barcodeId) throws WriterException, IOException {

        // Create a MultiFormatWriter instance to generate the barcode
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();

        // Encode the barcodeId (content) with the desired barcode format
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeId, BarcodeFormat.CODE_128, 300, 100);

        // Convert the BitMatrix to a BufferedImage (barcode image)
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Create a new BufferedImage with extra space for the text
        int imageWidth = barcodeImage.getWidth();
        int imageHeight = barcodeImage.getHeight() + 30; // Add 30px for the text area
        BufferedImage combinedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        // Draw the barcode and text on the new image
        Graphics2D g = combinedImage.createGraphics();

        // Draw the barcode on the image
        g.drawImage(barcodeImage, 0, 0, null);

        // Set font and color for the text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        // Calculate the position to center the text below the barcode
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(barcodeId);
        int xPosition = (imageWidth - textWidth) / 2;
        int yPosition = barcodeImage.getHeight() + fontMetrics.getHeight();

        // Draw the text below the barcode
        g.drawString(barcodeId, xPosition, yPosition);

        // Dispose of the graphics context
        g.dispose();

        // Convert the combined image to a byte array (PNG format)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(combinedImage, "PNG", baos);

        // Return the byte array representing the barcode image with text
        return baos.toByteArray();
    }
}
