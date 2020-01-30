package com.axelor.app.gst.controller;

import com.axelor.app.gst.sequence.service.GstSequenceService;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class GstSequenceController {
  @Inject GstSequenceService gstSequenceService;

  public void setGstPartysequence(ActionRequest request, ActionResponse response) {
    try {
      Party party = request.getContext().asType(Party.class);
      if (party.getReference() == null) {
        MetaModel metaModel;
        metaModel = Beans.get(MetaModelRepository.class).findByName("Party");
        Sequence sequence;
        sequence =
            Beans.get(SequenceRepository.class)
                .all()
                .filter("self.model = ?", metaModel)
                .fetchOne();
        if (sequence == null) {
          throw new NullPointerException("demo");
        }
        party = gstSequenceService.setGstPartySequence(party, sequence);
      }
      response.setValues(party);
    } catch (NullPointerException e) {
      response.setError("Party Model Are Not Available.");
    }
  }

  public void setGstInvoicesequence(ActionRequest request, ActionResponse response) {
    try {
      //  System.err.println("datagaya");
      Invoice invoice = request.getContext().asType(Invoice.class);
      if (invoice.getReference() == null) {
        MetaModel metaModel;
        metaModel = Beans.get(MetaModelRepository.class).findByName("Invoice");
        Sequence sequence;
        sequence =
            Beans.get(SequenceRepository.class)
                .all()
                .filter("self.model = ?", metaModel)
                .fetchOne();
        if (sequence == null) {
          throw new NullPointerException("demo");
        }
        invoice = gstSequenceService.setGstInvoiceSequence(invoice, sequence);
      }
      invoice.setStatus("Validated");
      response.setValues(invoice);
    } catch (NullPointerException e) {
      response.setError("Invoice Model Are Not Available.");
    }
  }
}
