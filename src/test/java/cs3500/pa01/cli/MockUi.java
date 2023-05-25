package cs3500.pa01.cli;

import cs3500.pa01.spacedrep.Question;
import cs3500.pa01.spacedrep.Session;
import java.io.IOException;

public class MockUi extends UiController {
  @Override
  public int getMax() {
    return 1;
  }

  @Override
  public String getPath() {
    return "sampleData/srgeneration/example.sr";
  }

  @Override
  public void updateQuestion(Question q, Session s) throws IOException {
    throw new IOException();
  }
}
