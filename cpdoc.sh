#!/bin/bash

for f in .doc/*/*.pdf; do
	cp "$f" Deliverables/
done

