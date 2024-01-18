package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import music.musicapp.service.interfaceService.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

}
