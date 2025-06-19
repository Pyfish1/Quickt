package pyfish.quickt;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class QuicktClient implements ClientModInitializer {
	private static KeyBinding quitKey;
	private static final Text LEAVING_TEXT = Text.of("Saving World...");

	@Override
	public void onInitializeClient() {
		quitKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.quickt.quit",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_M,
				"category.quickt.keys"
		));

		ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> checkKeyPress());
	}

	public static void checkKeyPress() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client == null) {
			return;
		}

		if (quitKey.wasPressed()){
			if (client.world != null) {
				boolean sp = client.isInSingleplayer();
				client.world.disconnect();
				client.disconnect(new MessageScreen(LEAVING_TEXT));
				TitleScreen titleScreen = new TitleScreen();
				client.setScreen(new TitleScreen());
			}
		}
	}
}