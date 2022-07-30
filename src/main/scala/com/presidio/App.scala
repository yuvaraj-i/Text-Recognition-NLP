package com.presidio

import epic.models.{NerSelector, PosTagSelector}
import epic.preprocess.{MLSentenceSegmenter, TreebankTokenizer}

import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    val URL = "https://norvig.com/big.txt"

    val sourceConnection = Source.fromURL(URL)
    val text = sourceConnection.mkString

    val sentenceSplitter = MLSentenceSegmenter.bundled().get
    val tokenizer = new TreebankTokenizer()
    val sentences = sentenceSplitter(text).map(tokenizer).toIndexedSeq
    val tagger = PosTagSelector.loadTagger("en").get
    val ner = NerSelector.loadNer("en").get
//    val ner = Segmenter.nerSystem(NerSelector.loadNer("en").get.asInstanceOf[SemiCRF[String, String]])

    sentences.foreach(sentence =>  {
      println(sentence.mkString(" "))

      val tags = tagger.bestSequence(sentence)
      println(tags.render)

      println()

      val entity = ner.bestSequence(sentence)
      println(entity)
    })

    sourceConnection.close()
  }
}
