package xyz.algogo.desktop;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import xyz.algogo.desktop.dialogs.ErrorDialog;
import xyz.algogo.desktop.frames.EditorFrame;
import xyz.algogo.desktop.utils.Utils;

public class AlgogoDesktop {
	
	public static final String APP_NAME = "Algogo Desktop";
	public static final String APP_VERSION = "0.3";
	public static final String[] APP_AUTHORS = new String[]{"Skyost"};
	public static final String APP_WEBSITE = "http://www.algogo.xyz";
	
	public static final List<Image> ICONS = buildIconsList();
	
	private static Font consoleFont;
	private static AppSettings settings;
	
	public static final void main(final String[] args) {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

				@Override
				public final void uncaughtException(final Thread thread, final Throwable throwable) {
					ErrorDialog.errorMessage(null, throwable);
				}
				
			});
			settings = new AppSettings(new File(Utils.getParentFolder(), "settings.json"));
			settings.load();
			final Properties properties = new Properties();
			properties.put("logoString", APP_NAME);
			AcrylLookAndFeel.setTheme(properties);
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
			final Icon empty = new ImageIcon();
			UIManager.put("Tree.collapsedIcon", empty);
			UIManager.put("Tree.expandedIcon", empty);
			consoleFont = Font.createFont(Font.TRUETYPE_FONT, AlgogoDesktop.class.getResourceAsStream("/xyz/algogo/desktop/res/fonts/DejaVuSansMono.ttf")).deriveFont(12.0f);
			SwingUtilities.invokeLater(new Runnable() {
	
				@Override
				public final void run() {
					final EditorFrame frame = new EditorFrame();
					frame.setVisible(true);
					if(args == null || args.length == 0) {
						return;
					}
					final File file = new File(args[0]);
					if(file.exists()) {
						try {
							frame.open(file);
						}
						catch(final Exception ex) {
							ErrorDialog.errorMessage(frame, ex);
						}
					}
				}
				
			});
		}
		catch(final Exception ex) {
			ErrorDialog.errorMessage(null, ex);
		}
	}
	
	/**
	 * Gets the font used by the console.
	 * 
	 * @return The console font.
	 */
	
	public static final Font getConsoleFont() {
		return consoleFont;
	}
	
	/**
	 * Gets the settings.
	 * 
	 * @return The settings.
	 */
	
	public static final AppSettings getSettings() {
		return settings;
	}
	
	private static final List<Image> buildIconsList() {
		final List<Image> icons = new ArrayList<Image>();
		final Image icon = Toolkit.getDefaultToolkit().getImage(AlgogoDesktop.class.getResource("/xyz/algogo/desktop/res/icons/app_icon.png"));
		icons.addAll(Arrays.asList(
			icon.getScaledInstance(16, 16, Image.SCALE_SMOOTH),
			icon.getScaledInstance(32, 32, Image.SCALE_SMOOTH),
			icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH),
			icon.getScaledInstance(128, 128, Image.SCALE_SMOOTH),
			icon.getScaledInstance(256, 256, Image.SCALE_SMOOTH),
			icon//.getScaledInstance(512, 512, Image.SCALE_SMOOTH) // Already in 512x512.
		));
		return Collections.unmodifiableList(icons);
	}
	
}