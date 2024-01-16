package com.ra.service.email;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
            simpleMailMessage.setText(user.getResetTokenExpiry() + " Kính gửi " + user.getFullName() + ",\n\n"
                    + "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng nhấp vào liên kết bên dưới để đặt lại mật khẩu:\n\n"
                    + "Liên kết đặt lại: http://localhost:8080/reset-password?token=" + token + "&password=\n\n"
                    + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n"
                    + "Cảm ơn,\nSHOP BANG");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void senEmailOrder(OrderResponseDTO orderResponseDTO) {
        try {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailPort);
        simpleMailMessage.setTo(orderResponseDTO.getEmail());
        simpleMailMessage.setSubject(orderResponseDTO.getUserName());
        simpleMailMessage.setText(orderResponseDTO.getOrderDate() + " Kính gửi " + orderResponseDTO.getFullName() + ",\n\n"

                + "Cảm ơn,\nSHOP BANG");
        javaMailSender.send(simpleMailMessage);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
