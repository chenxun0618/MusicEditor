package cs3500.music.view;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * Mock Class for testing
 */
public class MidiMock implements IMusicEditorView {

  private final Synthesizer synth;
  private final ReceiverMock receiver;
  private IMusicEditorModel model;

  public MidiMock(IMusicEditorModel model) {
    Synthesizer s = null;

    try {
      s = MidiSystem.getSynthesizer();
      s.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.synth = s;
    this.receiver = new ReceiverMock();
    this.model = model;
  }


  public void playNote(Note n) throws InvalidMidiDataException {
    ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(),
            n.getPitch().getPitchValue(), n.getVolume());
    ShortMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(),
            n.getPitch().getPitchValue(), n.getVolume());
    this.receiver.send(start, -1);
    this.receiver.send(stop,
            this.synth.getMicrosecondPosition() + (n.getSustain() + 1) * model.getTempo());
  }


  /**
   * plays the music at the given beat
   * @param beat a beat
   * @throws InvalidMidiDataException
   */
  public void play(int beat) throws InvalidMidiDataException {
    if (beat == model.getDuration()) {
      this.receiver.close();
    }

    List<Note> notes = model.retrieveNotes(beat);
    for (Note n : notes) {
      if (n.getHead() == beat) {
        playNote(n);
      }
    }
  }

  //plays the whole song
  public void playMusic() throws InvalidMidiDataException {
    for (int i = 0; i < model.getDuration(); i++) {
      play(i);
    }
  }

  @Getter
  public ReceiverMock getReceiver(){
    return this.receiver;
  }

  @Override
  public void start() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reStart() {

  }

  @Override
  public int getCurrentBeat() {
    return 0;
  }

  @Override
  public void addNote(Note n) {

  }

  @Override
  public void removeNote(Note n) {

  }
}
