package screenControls;
import java.util.ArrayList;
public enum Menu{
	MAIN(null, 0), 
	PRICE(MAIN, 1), 
		PRICE_QUERY_ONE(PRICE, 13), 
		PRICE_QUERY_TWO(PRICE, 13), 
		PRICE_QUERY_THREE(PRICE, 15), 
	DATE_OF_SALE(MAIN, 2), 
		DATE_OF_SALE_QUERY_0NE(DATE_OF_SALE, 16), 
		DATE_OF_SALE_QUERY_TWO(DATE_OF_SALE, 17), 
		DATE_OF_SALE_QUERY_THREE(DATE_OF_SALE, 18),
	POSTCODE(MAIN, 3), 
	PROPERTY_TYPE(MAIN, 4), 
	OLD_NEW(MAIN, 5), 
	NUM_NAME(MAIN, 6),
	STREET(MAIN, 7), 
	LOCALITY(MAIN, 8), 
	TOWN(MAIN, 9), 
	DISTRICT(MAIN, 10), 
	COUNTY(MAIN, 11),
	SEARCH(MAIN,12);
	
	private int screenNumber;
	private Menu parentScreen;
	private ArrayList<Menu> childScreens = new ArrayList<Menu>();
	private Menu(Menu parentScreen, int screenNumber) {
		this.parentScreen = parentScreen;
		this.screenNumber = screenNumber;
		if (parentScreen != null)
		this.parentScreen.childScreens.add(this);
	}
	
	boolean isChild(Menu parentScreen){
		return this.parentScreen == parentScreen;
	}
	
	public Menu getParent(){
		return parentScreen;
	}
	
	public Menu[] getChildren(){
		return childScreens.toArray(new Menu[childScreens.size()]);
	}
	
	public int getScreenNumber(){
		return screenNumber;
	}
	
	public boolean hasChild(){
		return getChildren().length != 0;
	}
}