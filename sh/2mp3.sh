!/bin/bash
parallel -i -j$(nproc) ffmpeg -i {} -qscale:a 0 {}.mp3 -- ./*.flac
rename -v "s/\.flac.mp3$/\.mp3/" *.mp3
