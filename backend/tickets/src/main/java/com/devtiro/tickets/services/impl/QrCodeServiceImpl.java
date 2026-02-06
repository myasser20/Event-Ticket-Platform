package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.entities.QrCode;
import com.devtiro.tickets.domain.entities.QrCodeStatusEnum;
import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.exceptions.QrCodeGenerationException;
import com.devtiro.tickets.exceptions.QrCodeNotFoundException;
import com.devtiro.tickets.repositories.QrCodeRepository;
import com.devtiro.tickets.services.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private QrCodeRepository qrCodeRepository;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try{
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);

            QrCode qrCode = new QrCode();

            qrCode.setId(uniqueId);
            qrCode.setTicket(ticket);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);

            return qrCodeRepository.saveAndFlush(qrCode);



        } catch (IOException e) {
            throw new QrCodeGenerationException("Failed to generate qr code",e);
        } catch (WriterException e) {
            throw new QrCodeGenerationException("Failed to generate qr code",e);        }

    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket( UUID ticketId,UUID userId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow( QrCodeGenerationException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        }
        catch(IllegalArgumentException ex){
           log.error("invalid base 64 QR code for ticket ID: {}",ex, ticketId);
           throw new QrCodeNotFoundException();
        }


    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {

        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream() )
        {
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            return  Base64.getEncoder().encodeToString(imageBytes);
        }


    }
}
