package com.app.gst;

import com.axelor.app.AxelorModule;
import com.axelor.app.gst.service.InvoiceLine.GstInvoiceLineService;
import com.axelor.app.gst.service.InvoiceLine.GstInvoiceLineServiceImpl;
import com.axelor.app.gst.service.invoice.GstInvoiceService;
import com.axelor.app.gst.service.invoice.GstInvoiceServiceImpl;
import com.axelor.app.gst.service.sequence.GstSequenceRepository;
import com.axelor.app.gst.service.sequence.GstSequenceService;
import com.axelor.app.gst.service.sequence.GstSequenceServiceImpl;
import com.axelor.gst.db.repo.SequenceRepository;

public class GstConfig extends AxelorModule {
  @Override
  protected void configure() {
    // TODO Auto-generated method stub
    bind(SequenceRepository.class).to(GstSequenceRepository.class);
    bind(GstSequenceService.class).to(GstSequenceServiceImpl.class);
    bind(GstInvoiceService.class).to(GstInvoiceServiceImpl.class);
    bind(GstInvoiceLineService.class).to(GstInvoiceLineServiceImpl.class);
    super.configure();
  }
}
