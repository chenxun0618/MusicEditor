package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicEditorView {
  private IMusicEditorModel model;
  private Sequencer sequencer;

  /**
   * a MidiViewImpl constructor that takes a model
   * @param model an IMusicEditorModel
   * @throws InvalidMidiDataException if the midi data is invalid
   */
  public MidiViewImpl(IMusicEditorModel model) throws InvalidMidiDataException {
    this.model = model;

    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    try {
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setTempoInMPQ(model.getTempo() / 4);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    Track track = sequence.createTrack();
    addNotes(track);
    sequencer.setSequence(sequence);
  }


  /**
   * makes MidiEvents with the given note
   * @param n a note
   * @throws InvalidMidiDataException if the midi data is invalid
   */

  private List<MidiEvent> makeMidiEvents(Note n) throws InvalidMidiDataException {
    List<MidiEvent> notes = new ArrayList<>();
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(),
            n.getPitch().getPitchValue(), n.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(),
            n.getPitch().getPitchValue(), n.getVolume());
    MidiEvent midiNote = new MidiEvent(start, n.getHead() * 4);
    MidiEvent midiNote2 = new MidiEvent(stop, (n.getHead() + n.getSustain() + 1) * 4);
    notes.add(midiNote);
    notes.add(midiNote2);
    return notes;
  }

  /**
   * adds all the notes in the song to the given track
   * @param track a track
   * @throws InvalidMidiDataException if the midi data is invalid
   */
  private void addNotes(Track track) throws InvalidMidiDataException {
    for (int i = 0; i <= model.getDuration(); i++) {
      for (Note n : model.retrieveNotes(i)) {
        if (n.getHead() == i) {
          List<MidiEvent> midiEvents = makeMidiEvents(n);
          midiEvents.forEach(track::add);
        }
      }
    }
  }

  @Override
  public void start() {
    sequencer.start();
    sequencer.setTempoInMPQ(model.getTempo() / 4);
  }

  @Override
  public void pause() {
    sequencer.stop();
  }

  @Override
  public void reStart() {
    sequencer.setTickPosition(0);
    start();
  }

  @Override
  public int getCurrentBeat() {
    return (int) this.sequencer.getTickPosition() / 4;
  }

  @Override
  public void addNote(Note n) {
    Track[] track = sequencer.getSequence().getTracks();
    List<MidiEvent> midiEvents = null;
    try {
      midiEvents = makeMidiEvents(n);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    if (midiEvents != null) {
      midiEvents.forEach(track[0]::add);
    }
  }

  @Override
  public void removeNote(Note n) {
    Track[] track = sequencer.getSequence().getTracks();
    List<MidiEvent> midiEvents = null;
    try {
      midiEvents = makeMidiEvents(n);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < track[0].size(); i++) {
      if (midiEvents != null) {
        for (MidiEvent event : midiEvents) {
          if (track[0].get(i).getMessage().getMessage()[0] ==
                  event.getMessage().getMessage()[0] &&
                  track[0].get(i).getMessage().getMessage()[1] ==
                          event.getMessage().getMessage()[1] &&
                  track[0].get(i).getMessage().getMessage()[2] ==
                          event.getMessage().getMessage()[2] &&
                  track[0].get(i).getTick() == event.getTick()) {
            track[0].remove(track[0].get(i));
          }
        }
      }
    }
  }
}