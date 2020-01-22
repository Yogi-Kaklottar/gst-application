package com.app.gst;

import com.axelor.app.AxelorModule;
import com.axelor.gst.db.repo.SequenceRepository;

public class GstConfig extends AxelorModule {
  @Override
  protected void configure() {
    bind(SequenceRepository.class).to(Sequencedata.class);
    super.configure();
  }
}
