import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class Bill {

	private String email;
	private String raisedOn;
	private Integer amount;
	private String settledOn;
	private String paymentStatus;
	private String dueDate;

}
