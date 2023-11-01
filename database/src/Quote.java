import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class Quote {

	protected String modifiedTime;
	protected String createdTime;
	protected String description;
	protected String userID;
	protected String status;

}