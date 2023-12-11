import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class QuoteRequest {

	private String requestType;
	private String createdOn;
	private String description;
	private String email;
	private String status;
	private String comment;
	private String userComment;
	private Integer treeID;
	private Integer quoteRequestID;
	private Integer proposedPrice;
}