package com.app.gst;

import com.axelor.app.AxelorModule;
import com.axelor.app.gst.InvoiceLine.service.GstInvoiceLineService;
import com.axelor.app.gst.InvoiceLine.service.GstInvoiceLineServiceImpl;
import com.axelor.app.gst.invoice.service.GstInvoiceService;
import com.axelor.app.gst.invoice.service.GstInvoiceServiceImpl;
import com.axelor.app.gst.sequence.service.GstSequenceRepository;
import com.axelor.app.gst.sequence.service.GstSequenceService;
import com.axelor.app.gst.sequence.service.GstSequenceServiceImpl;
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
