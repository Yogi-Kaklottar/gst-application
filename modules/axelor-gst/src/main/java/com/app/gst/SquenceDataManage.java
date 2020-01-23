package com.app.gst;

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
import com.google.inject.persist.Transactional;

public class SquenceDataManage {
  @Inject Sequencedata sdata;

  @Transactional
  public void setPartysequence(ActionRequest request, ActionResponse response) {
    sdata = new Sequencedata();
    Party party = request.getContext().asType(Party.class);
    Sequence sequence;
    if (party.getReference() == null) {
      MetaModel m;
      try {
        m = Beans.get(MetaModelRepository.class).findByName("Party");
        long id = m.getId();
        sequence = Beans.get(SequenceRepository.class).all().filter("self.model = ?", m).fetchOne();
        int n = Integer.parseInt(sequence.getNextnumber());
        String nextnumber = "" + n;
        int size = nextnumber.length();
        for (int i = size; i < sequence.getPadding(); i++) {
          nextnumber = "0" + nextnumber;
        }
        String s = sequence.getPrefix() + nextnumber + sequence.getSuffix();
        n = n + 1;
        String num = "" + n;
        sequence.setNextnumber(num);
        sdata.save(sequence);
        party.setReference(s);
        response.setValues(party);

      } catch (NullPointerException e) {
        response.setError(
            "Party Model Are Not Available Please Generate Party Sequence Model Class");
      }
    }
  }

  @Transactional
  public void setinvoiceSequence(ActionRequest request, ActionResponse response) {
    sdata = new Sequencedata();
    Sequence sequence;

    Invoice invoice = request.getContext().asType(Invoice.class);
    if (invoice.getReference() == null) {
      MetaModel m;
      try {
        m = Beans.get(MetaModelRepository.class).findByName("Invoice");
        long id = m.getId();
        sequence = Beans.get(SequenceRepository.class).all().filter("self.model = ?", m).fetchOne();
        int n = Integer.parseInt(sequence.getNextnumber());
        String nextnumber = "" + n;
        int size = nextnumber.length();
        for (int i = size; i < sequence.getPadding(); i++) {
          nextnumber = "0" + nextnumber;
        }
        String s = sequence.getPrefix() + nextnumber + sequence.getSuffix();
        n = n + 1;
        String num = "" + n;
        sequence.setNextnumber(num);
        sdata.save(sequence);
        invoice.setReference(s);
        invoice.setStatus("Validated");
        response.setValues(invoice);
      } catch (NullPointerException e) {
        response.setError(
            "Invoice Model Are Not Available Please Generate Invoice Sequence Model Class");
      }
    }
  }

  public void setInvoicePaid(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    invoice.setStatus("Paid");
    response.setValues(invoice);
  }

  public void setInvoiceCancel(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    invoice.setStatus("Cancelled");
    response.setValues(invoice);
  }
}
