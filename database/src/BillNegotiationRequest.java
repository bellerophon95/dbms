import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class BillNegotiationRequest {

	private String email;
	private String createdOn;
	private String description;
	private String status;
	private String requestType;
	private Integer quoteRequestID;

}
