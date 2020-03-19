package nl.plaatsoft.knightsquest.tools;

import javafx.scene.media.AudioClip;
import nl.plaatsoft.knightsquest.model.Player;

public class MySound {

	public static final int CLIP_CASTLE = 1;
	public static final int CLIP_CREATE = 2;
	public static final int CLIP_FUNERAL = 3;
	public static final int CLIP_DIE = 4;
	public static final int CLIP_END = 5;
	public static final int CLIP_FIGHT = 6;
	public static final int CLIP_START = 7;
	public static final int CLIP_STEP = 8;
	public static final int CLIP_UPGRADE = 9;
	public static final int CLIP_TURN = 10;

	private static AudioClip castle = new AudioClip(MySound.class.getResource("/sounds/castle.mp3").toExternalForm());
	private static AudioClip create = new AudioClip(MySound.class.getResource("/sounds/step.mp3").toExternalForm());
	private static AudioClip funeral = new AudioClip(MySound.class.getResource("/sounds/cross.mp3").toExternalForm());
	private static AudioClip die = new AudioClip(MySound.class.getResource("/sounds/die.mp3").toExternalForm());
	private static AudioClip end = new AudioClip(MySound.class.getResource("/sounds/end.mp3").toExternalForm());
	private static AudioClip fight = new AudioClip(MySound.class.getResource("/sounds/fight.mp3").toExternalForm());
	private static AudioClip start = new AudioClip(MySound.class.getResource("/sounds/start.mp3").toExternalForm());
	private static AudioClip step = new AudioClip(MySound.class.getResource("/sounds/step.mp3").toExternalForm());
	private static AudioClip upgrade = new AudioClip(MySound.class.getResource("/sounds/upgrade.mp3").toExternalForm());
	private static AudioClip turn = new AudioClip(MySound.class.getResource("/sounds/turn.mp3").toExternalForm());
		
	static public void play(Player player, int sound) {

		//if (player.getType()!=PlayerEnum.BOT && MyFactory.getSettingDAO().getSettings().isSoundEffectsOn()) {
		if (MyFactory.getSettingDAO().getSettings().isSoundEffectsOn()) {

			switch (sound) {

			case CLIP_CASTLE: 
				castle.play();
				break;

			case CLIP_CREATE:
				create.play();
				break;
				
			case CLIP_FUNERAL:
				funeral.play();
				break;
				
			case CLIP_DIE:
				die.play();
				break;

			case CLIP_END:
				end.play();
				break;

			case CLIP_FIGHT:
				fight.play();
				break;

			case CLIP_START:
				start.play();
				break;

			case CLIP_STEP:
				step.play();
				break;

			case CLIP_UPGRADE:
				upgrade.play();
				break;
				
			case CLIP_TURN:
				turn.play();
				break;				
			}
		}
	}
}
