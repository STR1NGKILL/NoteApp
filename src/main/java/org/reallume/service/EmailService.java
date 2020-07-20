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
                "                                                    Регистрация\n" +
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
                "Уважаемый, " + user + ". Вы сделали запрос на регистрацию на сайте NoteApp. Благодарим, что Вы с нами!</p>\n" +
                "                                                            </td>\n" +
                "                                                            <td width=\"30\"><p style=\"margin:0; font-size:1px; line-height:1px;\">&nbsp;</p></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"text-align:center; font-size:16px; line-height:30px; font-weight:bold; color:#344860;\">Для активации аккаунта пожалуйста перейдите по ссылке:</p>\n" +
                "                <p style=\"text-align:center; font-size:22px; line-height:30px; font-weight:bold; color:#344860;\"><a href=\"" + link + "\">" + link + "</a></p>\n" +
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
                "\t\t\t\t\t\t\t\t\t\t\tЕсли Вы не делали запроса на регистрацию, то можете удалить данное письмо. </p>\n" +
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
