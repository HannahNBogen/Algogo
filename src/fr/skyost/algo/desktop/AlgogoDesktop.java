package fr.skyost.algo.desktop;

import java.awt.Font;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import fr.skyost.algo.desktop.dialogs.ErrorDialog;
import fr.skyost.algo.desktop.frames.EditorFrame;

public class AlgogoDesktop {
	
	public static final String APP_NAME = "Algogo Desktop";
	public static final String APP_VERSION = "0.1.3";
	public static final String[] APP_AUTHORS = new String[]{"Skyost"};
	public static final String APP_WEBSITE = "http://www.algogo.xyz";
	
	public static Font CONSOLE_FONT;
	public static AppSettings SETTINGS;
	
	public static final void main(final String[] args) {
		try {
			SETTINGS = new AppSettings(new File(new File(URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), StandardCharsets.UTF_8.toString())), "settings.json"));
			SETTINGS.load();
			//SETTINGS.save(); // Used to save new fields, will be used in next versions.
			final Properties properties = new Properties();
			properties.put("logoString", APP_NAME);
			AcrylLookAndFeel.setTheme(properties);
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
			final Icon empty = new ImageIcon();
			UIManager.put("Tree.collapsedIcon", empty);
			UIManager.put("Tree.expandedIcon", empty);
			CONSOLE_FONT = Font.createFont(Font.TRUETYPE_FONT, AlgogoDesktop.class.getResourceAsStream("/fr/skyost/algo/desktop/res/fonts/DejaVuSansMono.ttf")).deriveFont(12.0f);
			SwingUtilities.invokeLater(new Runnable() {
	
				@Override
				public final void run() {
					final EditorFrame frame = new EditorFrame();
					frame.setVisible(true);
					if(args != null && args.length > 0) {
						final File file = new File(args[0]);
						if(file.exists()) {
							frame.open(file);
						}
					}
				}
				
			});
		}
		catch(final Exception ex) {
			ex.printStackTrace();
			ErrorDialog.errorMessage(null, ex);
		}
	}
	
}