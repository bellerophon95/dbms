import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class Quote {

	private String modifiedTime;
	private String createdTime;
	private String description;
	private String email;
	private String status;
	private String requestType;
	private Integer agreedPrice;
	private Integer engagements;
	private Integer treeID;
	private Integer quoteRequestID;

}