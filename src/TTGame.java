import java.util.Random;


public class TTGame {
	
	TTController controller;
	
	String[] words = {"HARNESK","MÅNNE", "VARMKORV", "ÖVERDOS", "FÖDELSEDAG", "BINDEMEDEL", "APOSLAGÄRNINGARNA", "VARNINGSSIGNAL", "MATLAGNING", "KRETS", "DALGÅNG", 
			"HUVUDSTYRKA", "LUTHERHJÄLPEN", "LÄCKA", "ARBETSVILLKOR", "ARRANGEMANG", "KAMMARKÖR", "LESBISK", "VIDEOBANDSPELARE", "OSÖKT", "ANNALKANDE", "CESIUM", "HÖGERBACK",
			"NEUTRAL", "PEDAL", "KYRKOHERDE", "LITOGRAFI", "HEDERSLEDAMOT", "BARNOMSORG", "AVLASTA", "AVSTAMP", "FÖRSÖKSVERKSAMHET", "SKÖRD", "ADDITIV", "REDOVISNING"};
	
	Random rand = new Random();
	int difficulty;
	
	public String currentWord;
	public int currentIndex;
	
	public TTGame(int difficulty) {
		this.difficulty = difficulty;
		changeCurrentWord();
	}
	
	private void changeCurrentWord() {
		currentWord = words[rand.nextInt(words.length)];
		currentIndex = 0;
		System.out.println("New word: " + currentWord);
	}
	
	public boolean matchLetter(char letter) {
		if (currentWord.charAt(currentIndex) == letter) {
			currentIndex++;
			
			if (currentIndex == currentWord.length()) {
				
				changeCurrentWord();
			}
			
			return true;
		}
		return false;
	}
	

}
