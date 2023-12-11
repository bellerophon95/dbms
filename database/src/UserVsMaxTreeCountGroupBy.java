import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserVsMaxTreeCountGroupBy {

	protected String password;
	protected String email;
	protected String firstName;
	protected String lastName;
	protected String adress_street_num;
	protected String adress_street;
	protected String adress_city;
	protected String adress_state;
	protected String adress_zip_code;
	protected String birthday;
	protected int cash_bal;
	protected int PPS_bal;
	protected String role;
	private Integer maxTreeCount;

}
