























import os

from sigal import signals
from sigal.writer import AbstractWriter
from sigal.utils import url_from_path


class PageWriter(AbstractWriter):
    


    template_file = "media.html"

    def write(self, album, media_group):
        


        from sigal import __url__ as sigal_link
        file_path = os.path.join(album.dst_path, media_group[0].filename)

        page = self.template.render({
            'album': album,
            'media': media_group[0],
            'previous_media': media_group[-1],
            'next_media': media_group[1],
            'index_title': self.index_title,
            'settings': self.settings,
            'sigal_link': sigal_link,
            'theme': {'name': os.path.basename(self.theme),
                      'url': url_from_path(os.path.relpath(self.theme_path,
                                                           album.dst_path))},
        })

        output_file = "%s.html" % file_path

        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(page)


def generate_media_pages(gallery):
    


    writer = PageWriter(gallery.settings, index_title=gallery.title)

    for album in gallery.albums.values():
        medias = album.medias
        next_medias = medias[1:] + [None]
        previous_medias = [None] + medias[:-1]

        
        media_groups = zip(medias, next_medias, previous_medias)

        for media_group in media_groups:
            writer.write(album, media_group)


def register(settings):
    signals.gallery_build.connect(generate_media_pages)
