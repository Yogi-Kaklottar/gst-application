package com.app.gst;

import com.app.service.ServiceData;
import com.app.service.ServiceImpl;
import com.axelor.app.AxelorModule;
import com.axelor.gst.db.repo.SequenceRepository;

public class GstConfig extends AxelorModule {
  @Override
  protected void configure() {
    bind(SequenceRepository.class).to(Sequencedata.class);
    bind(ServiceData.class).to(ServiceImpl.class);
    super.configure();
  }
}
