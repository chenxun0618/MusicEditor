package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.music.util.CompositionBuilder;

/**
 * to represent a piece of music that has a duration, a list of notes in this music
 * and a tempo in microseconds per beat
 */
public final class Music implements IMusicEditorModel {
  private int duration;
  private List<Note> notes;
  private int tempo;
  public enum MixType {
    simultaneously, consecutively
  }

  //default constructor
  public Music() {
    this.duration = 0;
    this.tempo = 100000;
    this.notes = new ArrayList<>();
  }

  /**
   * takes a list of notes, sorts the list and computes the duration of the music
   * @param notes a list of notes
   * @throws IllegalArgumentException if the list is empty
   */
  public Music(List<Note> notes) {
    if (notes.isEmpty()) {
      throw new IllegalArgumentException("Invalid notes");
    }
    Collections.sort(notes);
    this.notes = notes;
    this.tempo = 100000;
    this.duration = this.getDuration();
  }

  @Override
  public int getDuration() {
    int idx = 0;
    int max = -1;
    for (int i = 0; i < notes.size(); i++) {
      if (notes.get(i).getHead() > max) {
        max = notes.get(i).getHead();
        idx = i;
      }
    }
    return notes.get(idx).getHead() + notes.get(idx).getSustain();
  }

  @Override
  public void addNotes(List<Note> notes) {
    this.notes.addAll(notes);
    Collections.sort(this.notes);
    this.duration = this.getDuration();
  }

  @Override
  public void removeNotes(List<Note> notes) {
    this.notes.removeAll(notes);
    this.duration = this.getDuration();
  }

  @Override
  public Music mix(Music that, MixType type) {
    Music result = this;
    if (type == MixType.simultaneously) {
      result.addNotes(that.notes);
    }
    else if (type == MixType.consecutively) {
      for (Note n : that.notes) {
        n.setHead(n.getHead() + result.duration + 1);
      }
      result.addNotes(that.notes);
    }
    return result;
  }

  @Override
  public List<Note> retrieveNotes(int beat) {
    List<Note> result = new ArrayList<>();
    for (Note n : this.notes) {
      if (n.playedAt(beat)) {
        result.add(n);
      }
    }
    return result;
  }

  @Override
  public List<Pitch> getPitches() {
    List<Pitch> result = new ArrayList<>();
    int firstPitch = this.notes.get(0).getPitch().getPitchValue();
    int lastPitch = this.notes.get(this.notes.size() - 1).getPitch().getPitchValue();
    int pitchNum = lastPitch - firstPitch;

    for (int i = 0; i <= pitchNum; i++) {
      Pitch p = Pitch.buildPitch(i + firstPitch);
      result.add(p);
    }

    for (Pitch p : result) {
      List<Note> pitchNotes = new ArrayList<>();
      for (Note n : this.notes) {
        if (n.getPitch().equals(p)) {
          pitchNotes.add(n);
        }
      }
      p.setNotes(pitchNotes);
    }

    return result;
  }

  @Override
  public String printMusic() {
    StringBuilder result = new StringBuilder();
    result.append("  ");
    List<Pitch> pitchList = this.getPitches();

    for (Pitch p : pitchList) {
      if (p.printPitch().length() == 2) {
        result.append(String.format("  %s ", p.printPitch()));
      }
      if (p.printPitch().length() == 3) {
        result.append(String.format(" %s ", p.printPitch()));
      }
      if (p.printPitch().length() == 4) {
        result.append(String.format(" %s", p.printPitch()));
      }
    }

    for (int i = 0; i <= duration; i++) {
      result.append(String.format("\n%2d", i));
      for (Pitch p : pitchList) {
        if (p.playedAt(i)) {
          for (Note n : p.getNotes()) {
            if (n.getHead() == i) {
              result.append("  X  ");
              break;
            }
            else if (n.playedAt(i)) {
              result.append("  |  ");
              break;
            }
          }
        }
        else {
          result.append("     ");
        }
      }
    }

    return result.toString();
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getTempo() {
    return tempo;
  }

  public static final class Builder implements CompositionBuilder<IMusicEditorModel> {
    private IMusicEditorModel music;

    public Builder() {
      this.music = new Music();
    }

    @Override
    public IMusicEditorModel build() {
      return music;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
      music.setTempo(tempo);
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> addNote(int start, int end,
                                                         int instrument, int pitch, int volume) {
      Note n = new Note(start, end - start - 1, instrument - 1, Pitch.buildPitch(pitch), volume);
      List<Note> notes = new ArrayList<>();
      notes.add(n);
      music.addNotes(notes);
      return this;
    }
  }
}
