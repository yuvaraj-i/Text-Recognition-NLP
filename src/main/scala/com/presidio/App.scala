package com.presidio

import epic.models.{ParserSelector, PosTagSelector}
import epic.preprocess.{MLSentenceSegmenter, TreebankTokenizer}

object App {
  def main(args: Array[String]): Unit = {

    val text: String = "hi my name i yuvaraj"

    val sentenceSplitter = MLSentenceSegmenter.bundled().get
    val tokenizer = new TreebankTokenizer()
    val sentences = sentenceSplitter(text).map(tokenizer).toIndexedSeq


    for(sentence <- sentences) {
      println(sentence)

      val tagger = PosTagSelector.loadTagger("en").get

      val tags = tagger.bestSequence(sentence)

      println(tags.render)
    }



  }
}
