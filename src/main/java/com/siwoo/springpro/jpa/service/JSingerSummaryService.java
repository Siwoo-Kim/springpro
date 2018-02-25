package com.siwoo.springpro.jpa.service;

import com.siwoo.springpro.jpa.view.SingerSummary;

import java.util.List;

public interface JSingerSummaryService {
    List<SingerSummary> findAll();
}
