package com.ra.service.email;

import com.ra.dto.respose.orders.OrderDetailResponseDTO;
import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService{
    @Value("${email}")
    private String emailPort;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendEmail() {
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom(emailPort);
            simpleMailMessage.setTo("tinhtt336@gmail.com");
            simpleMailMessage.setText("chi tinh");
            simpleMailMessage.setSubject("bang ");
            javaMailSender.send(simpleMailMessage);
            return "cam on quy khach";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendEmailPass(User user, String token) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailPort);
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setSubject("Password Reset");
            simpleMailMessage.setText(" Kính gửi " + user.getFullName() + ",\n\n"
                    + "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng nhấp vào liên kết bên dưới để đặt lại mật khẩu:\n\n"
                    + "Liên kết đặt lại: http://localhost:8080/reset-password?token=" + token + "&password=\n\n" + " .Tồn tại trong 3 phút."
                    + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n"
                    + "Cảm ơn,\nSHOP BANG");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void senEmailOrder(OrderResponseDTO orderResponseDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            mimeMessageHelper.setFrom(emailPort);
            mimeMessageHelper.setTo(orderResponseDTO.getEmail());
            mimeMessageHelper.setSubject(orderResponseDTO.getOrderDate() + " Xác nhận đơn hàng - SHOP BANG");

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body>");
            emailContent.append("<p>Kính gửi ").append(orderResponseDTO.getFullName()).append(",</p>")
                    .append("<p>Cảm ơn bạn đã đặt hàng tại SHOP BANG. Dưới đây là chi tiết đơn hàng của bạn:</p>");
            emailContent.append("<table style='border-collapse: collapse; width: 100%;'>");
            emailContent.append("<tr>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Sản phẩm</th>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Số lượng</th>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Size</th>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Màu</th>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Đơn giá</th>")
                    .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Thành tiền</th>")
                    .append("</tr>");

            for (OrderDetailResponseDTO orderItem : orderResponseDTO.getOrderDetailResponseDTOS()) {
                emailContent.append("<tr>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(orderItem.getProductName()).append("</td>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(orderItem.getQuantity()).append("</td>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(orderItem.getSizeName()).append("</td>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(orderItem.getColorName()).append("</td>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(currencyFormatter.format(orderItem.getPrice())).append("</td>")
                        .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                        .append(currencyFormatter.format(orderItem.getTotalPrice())).append("</td>")
                        .append("</tr>");
            }

            emailContent.append("</table>");
            emailContent.append("<p>Tổng đơn hàng: ").append(currencyFormatter.format(orderResponseDTO.getTotal())).append("</p>")
                    .append("<p>Cảm ơn bạn đã mua sắm tại SHOP BANG!</p>")
                    .append("<p>Trân trọng,</p><p>SHOP BANG</p>");
            emailContent.append("</body></html>");

            mimeMessageHelper.setText(emailContent.toString(), true);
            javaMailSender.send(mimeMessage);

        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
