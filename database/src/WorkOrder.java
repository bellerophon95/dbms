import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class WorkOrder {

	private String email;
	private String createdOn;
	private Integer treeID;
	private String dueDate;
	private String description;
	private String orderStatus;
	private String completedOn;

}
