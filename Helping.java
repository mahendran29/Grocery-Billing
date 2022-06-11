package Helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helping
{
    public boolean validatingNumber(String text)
    {
        return text.matches("\\d+$");
    }
    public String getDate()
    {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return localDate.format(formatter);
    }

    public boolean validateMobileNumber(String phoneNumber)
    {
        Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = pattern.matcher(phoneNumber);
        return (match.find() && match.group().equals(phoneNumber));
    }
}
