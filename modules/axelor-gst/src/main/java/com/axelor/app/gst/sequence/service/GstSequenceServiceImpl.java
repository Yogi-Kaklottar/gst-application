package com.axelor.app.gst.sequence.service;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class GstSequenceServiceImpl implements GstSequenceService {
  @Inject GstSequenceRepository gstSequenceRepository;

  @Transactional
  public Party setGstPartySequence(Party party, Sequence sequence) {
    try {

      //      Sequence sequence;
      //      sequence = Beans.get(SequenceRepository.class).all().filter("self.model = ?",
      // metaModel).fetchOne();
      //  System.out.println(sequence);
      int n = Integer.parseInt(sequence.getNextnumber());
      String nextnumber = "" + n;
      int size = nextnumber.length();
      for (int i = size; i < sequence.getPadding(); i++) {
        nextnumber = "0" + nextnumber;
      }
      String stringReference = "";
      if (sequence.getPrefix() == null) {
        stringReference = "" + nextnumber;
      } else {
        stringReference = sequence.getPrefix() + nextnumber;
      }
      if (sequence.getSuffix() != null) {
        stringReference = stringReference + sequence.getSuffix();
      }
      n = n + 1;
      String num = "" + n;
      sequence.setNextnumber(num);
      gstSequenceRepository.save(sequence);
      party.setReference(stringReference);
    } catch (Exception e) {

      // System.err.println(e.fillInStackTrace());
    }
    return party;
  }

  @Override
  @Transactional
  public Invoice setGstInvoiceSequence(Invoice invoice, Sequence sequence) {
    try {

      int n = Integer.parseInt(sequence.getNextnumber());
      String nextnumber = "" + n;
      int size = nextnumber.length();
      for (int i = size; i < sequence.getPadding(); i++) {
        nextnumber = "0" + nextnumber;
      }
      String stringReference = "";
      if (sequence.getPrefix() == null) {
        stringReference = "" + nextnumber;
      } else {
        stringReference = sequence.getPrefix() + nextnumber;
      }
      if (sequence.getSuffix() != null) {
        stringReference = stringReference + sequence.getSuffix();
      }
      n = n + 1;
      String num = "" + n;
      sequence.setNextnumber(num);
      gstSequenceRepository.save(sequence);
      invoice.setReference(stringReference);
      // System.err.println("2");
    } catch (Exception e) {
      // System.err.println(e.fillInStackTrace());
    }
    return invoice;
  }
}