The pipeline could ingest variants from input/refsnp-chrMT.json and output them in variants.txt

{
"allele": {
  "spdi": {
    "seq_id": "YP_003024031.1",
    "position": 100,
    "deleted_sequence": "N",
    "inserted_sequence": "N"
  }
},
"hgvs": "YP_003024031.1:p.Asn101="
}
  
The **LinesProcessor** judge whether an allele is mutated by check whether the deleted_sequence or inserted_sequence is null.  
Use **ItemReader/ItemWriter** to do the I/O job, chunkSize should be configured in **application.properties**
