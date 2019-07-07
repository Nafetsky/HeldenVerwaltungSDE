package api.skills;

public interface Increasable {
	void setLevel(int level);

	String getName();

	ImprovementComplexity getComplexity();

	int getLevel();
}
