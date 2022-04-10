meta:
  id: sba
  application: NFSWM Engine
  endian: le


seq:
 - id: magic
   contents: ["SBIN", 3, 0, 0, 0]
 - id: a_enum
   type: header
 - id: stru
   type: header
 - id: fiel
   type: header
 - id: ohdr
   type: header
 - id: data
   type: header
 - id: chdr
   type: header
 - id: cdat
   type: header
types:
  header:
    seq:
      - id: ascii
        type: str
        size: 4
        encoding: ASCII
      - id: offset
        type: u4
      - id: unknown
        type: u4
      - id: content
        size: offset
