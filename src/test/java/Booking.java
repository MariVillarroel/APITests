import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Booking {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private Map<String, String> bookingdates;
    private String additionalneeds;
}