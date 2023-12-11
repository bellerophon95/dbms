import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class Tree {

	private Integer height;
	private String address;
	private Integer distToHouse;
	private Integer treeID;

}
