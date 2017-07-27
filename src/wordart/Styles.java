package wordart;

public enum Styles {
	One ("Thick, wavy blue text with drop shadow", 95, 209),//265),
		Two ("Rainbow text", 200, 209),//265),
		Three ("Bold, orange/yellow gradient text", 305, 209),//265),
		Four ("Black outlined text with white interior", 95, 300),//347),
		Five ("Arched text with black interior", 200, 300),//347),
		Six ("Skinny, pink to white gradient text with slight 3D effect", 305, 300),//347),
		Seven ("Bold, red/white/blue gradient text with noticable Z-axis", 95, 382),//429),
		Eight ("Upper-left slanted text with purple gradient and drop shadow", 200, 382),//429),
		Nine ("Thin, green text with large shadow behind it", 305, 382),//429),
		Ten ("Upper-left slanted and angled text with green/white texture and noticable Z-axis", 95, 464),//511),
		Eleven ("Upper-right slanted and angled text with silver/white gradient and slight Z-axis", 200, 464),//511),
		Twelve ("Upper-left slanted black text", 305, 464),//511),
		Thirteen ("Light blue outlined text with darker blue fill and burgundy drop shadow", 95, 546),//593),
		Fourteen ("Thin, wavy text with pine green/cornflower blue gradient", 200, 546),//593),
		Fifteen ("Italicized white text with black outline", 305, 546);//593);
	
	private String description;
	private int x, y;
	
	Styles(String description, int x, int y) {
		this.description = description;
		this.x = x;
		this.y = y;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
