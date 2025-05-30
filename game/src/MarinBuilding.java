import java.util.List;

public class MarinBuilding extends Building {

	public MarinBuilding(int x, int y, String team) {
		super(x, y, team);
		
	}
	@Override
	   public Unit update(List<Unit> buildUnit) {
	        if (buildingUnit) {
	            buildTimer--;
	            if (buildTimer <= 0) {
	                buildingUnit = false;

	                // 유닛 생성
	                Unit newUnit = new Unit_Marin(x + 40, y, team); // 옆에 배치
	                buildUnit.add(newUnit);
	                return newUnit;
	            }
	        }
	        return null;
	    }

}
