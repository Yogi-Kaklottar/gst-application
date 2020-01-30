package com.axelor.app.gst.sequence.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;

public interface GstSequenceService {
  public Party setGstPartySequence(Party party, Sequence sequence);

  public Invoice setGstInvoiceSequence(Invoice invoice, Sequence sequence);
}
