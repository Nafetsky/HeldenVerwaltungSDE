package controle;

import api.skills.ImprovementComplexity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddNewScriptDialogResult {
	private final String name;
	private final ImprovementComplexity costCategorie;
}
