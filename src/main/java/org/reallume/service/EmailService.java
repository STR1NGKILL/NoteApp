package org.reallume.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationMail(String toEmail, String user, String link) throws Exception {

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        helper.setTo(toEmail);
        helper.setSubject("Регистрация на сайте \"NoteApp\"");
        helper.setText("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "  <title>Подтверждение регистрации</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <p></p>\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "    <tbody>\n" +
                "      <tr>\n" +
                "        <td style=\"padding: 10px 0 30px 0;\">\n" +
                "          <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">\n" +
                "            <tbody>\n" +
                "              <tr>\n" +
                "                <td align=\"center\" bgcolor=\"#5cb885\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\"><img src=\"https://i.ibb.co/X2kp1k0/login-2.png\" width=\"150px&quot;\" height=\"150px\" alt=\"Creating Email Magic\" style=\"display: block;\" /></td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"height: 171px;\">\n" +
                "                    <tbody>\n" +
                "                      <tr style=\"height: 79px;\">\n" +
                "                        <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px; height: 79px;\">\n" +
                "                          <p><b>Спасибо за регистрацию<br />на сайте \"Компьютерные комплектующие\"!</b></p>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                      <tr style=\"height: 71px;\">\n" +
                "                        <td style=\"padding: 20px 0px 30px; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px; height: 71px;\">\n" +
                "                          <p>Для активации аккаунта пожалуйста перейдите по ссылке:&nbsp</p>\n" +
                "                          <p><a href=\"" + link + "\">" + link + "</a></p>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                      <tr style=\"height: 21px;\">\n" +
                "                        <td style=\"height: 21px;\"></td>\n" +
                "                      </tr>\n" +
                "                    </tbody>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                    <tbody>\n" +
                "                      <tr>\n" +
                "                        <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">&reg; Александр Кириллов</td>\n" +
                "                        <td align=\"right\" width=\"25%\">\n" +
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: bold; width: 39px;\"></td>\n" +
                "                                <td style=\"font-size: 0px; line-height: 0; width: 20px;\" width=\"20\">&nbsp;</td>\n" +
                "                                <td style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: bold; width: 39px;\"></td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                    </tbody>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </tbody>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "</body>\n" +
                "\n" +
                "</html>", true);

        mailMessage.setFrom("str1ngkill@yandex.ru");

        javaMailSender.send(mailMessage);
    }

    public String generateToken(String userName) {
        String datetime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String randomUUID = UUID.randomUUID().toString();

        String originalToken = userName + datetime + randomUUID;

        String ch = ".-!@#$%^&*()_+!№;%:?*/\\\"~";
        for (char c : ch.toCharArray()) {
            originalToken = originalToken.replace(c, ' ');
        }
        originalToken = originalToken.replaceAll(" ", "");

        return DigestUtils.md5Hex(originalToken).toUpperCase();
    }

    public void sendRecoveryMail(String toEmail, String user, String password) throws Exception {

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        helper.setTo(toEmail);
        helper.setSubject("Восстановление пароля на сайте \"NoteApp\"");
        helper.setText("<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body style=\"font-family:Gotham, 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color:#f0f2ea; margin:0; padding:0; color:#333333;\">\n" +
                "\n" +
                "<table width=\"100%\" bgcolor=\"#f0f2ea\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"padding:40px 0;\">\n" +
                "                <!-- begin main block -->\n" +
                "                <table cellpadding=\"0\" cellspacing=\"0\" width=\"608\" border=\"0\" align=\"center\">\n" +
                "                    <tbody>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                \n" +
                "                                <p style=\"margin:0 0 36px; text-align:center; font-size:14px; line-height:20px; text-transform:uppercase; color:#626658;\">\n" +
                "                                   \n" +
                "                                </p>\n" +
                "                                <!-- begin wrapper -->\n" +
                "                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td width=\"8\" height=\"4\" colspan=\"2\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"8\" height=\"4\" colspan=\"2\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                        </tr>\n" +
                "                                        \n" +
                "                                        <tr>\n" +
                "                                            <td width=\"4\" height=\"4\" style=\"background:url(../img/shadow-left-top.png) no-repeat 100% 0;\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td colspan=\"3\" rowspan=\"3\" bgcolor=\"#FFFFFF\" style=\"padding:0 0 30px;\">\n" +
                "                                                <!-- шапка -->\n" +
                "                                                <img src=\"http://2s2b.ru/upload/normal/0/moskva-kopirayter_na_yuridicheskiy_sayt_0.74372700%201522524257.jpeg\" width=\"600\" height=\"150\" alt=\"summer‘s coming trimm your sheeps\" style=\"display:block; border:0; margin:0 0 44px; background:#eeeeee;\">\n" +
                "                                                <p style=\"margin:0 30px 33px;; text-align:center; text-transform:uppercase; font-size:24px; line-height:30px; font-weight:bold; color:#484a42;\">\n" +
                "                                                    Восстановление пароля\n" +
                "                                                </p>\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr valign=\"top\">\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                            \n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                        </tr>\n" +
                "                                                        <tr valign=\"top\">\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                            <td colspan=\"3\">\n" +
                "                                \n" +
                "<p style=\"margin-left:20px; text-align:left; font-size:16px; line-height:30px; font-weight:bold; color:#344860;\">\n" +
                "Уважаемый, " + user + ". Вы сделали запрос на восстановление пароля на сайте NoteApp.</p>\n" +
                "                                                            </td>\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"text-align:center; font-size:16px; line-height:30px; font-weight:bold; color:#344860;\">Ваш новый пароль:</p>\n" +
                "                <p style=\"text-align:center; font-size:22px; line-height:30px; font-weight:bold; color:#344860;\">" + password + "</p>\n" +
                "               \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "                                                <!-- тело письма -->\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr valign=\"top\">\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                            \n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                        </tr>\n" +
                "                                                        <tr valign=\"top\">\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                            <td colspan=\"3\">\n" +
                "                                                                \n" +
                "             \n" +
                "\n" +
                "<p style=\"margin-left:20px; text-align:left; font-size:16px; line-height:30px; font-weight:bold; color:#344860;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\tЕсли Вы не делали запроса на получение нового пароля, то можете удалить данное письмо. Ваш пароль будет недоступен посторонним лицам.</p>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "                                                            </td>\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                                <!-- /конец тела письма -->\n" +
                "                                                \n" +
                "                                            </td>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                        </tr>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <tr>\n" +
                "                                            <td width=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                        </tr>\n" +
                "                                        \n" +
                "                                        <tr> \n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                        </tr>\n" +
                "                                 \n" +
                "                                        <tr>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                            <td width=\"4\" height=\"4\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <p style=\"margin:0; padding:34px 0 0; text-align:center; font-size:11px; line-height:13px; color:#333333;\">\n" +
                "                                 \n" +
                "                                </p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>", true);

        mailMessage.setFrom("str1ngkill@yandex.ru");

        javaMailSender.send(mailMessage);
    }
}
