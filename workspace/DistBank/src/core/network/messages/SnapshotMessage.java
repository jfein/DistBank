package core.network.messages;

/**
 * SnapshotMessage
 * @author verakutsenko, jeremyfein
 * This message is sent between branch servers that
 * either initiates a snapshot at the receiving branch or
 * plugs a channel in for that branch on which the receiving
 * branch stops recording any transactions received from the
 * sending branch
 *
 */
public class SnapshotMessage extends Message {

	private static final long serialVersionUID = 747422089297293739L;

}
