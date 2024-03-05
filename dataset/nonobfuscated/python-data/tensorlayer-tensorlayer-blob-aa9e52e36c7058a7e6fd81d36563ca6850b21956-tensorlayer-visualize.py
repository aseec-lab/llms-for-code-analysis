


import os

import imageio

import numpy as np

import tensorlayer as tl
from tensorlayer.lazy_imports import LazyImport
cv2 = LazyImport("cv2")





__all__ = [
    'read_image',
    'read_images',
    'save_image',
    'save_images',
    'draw_boxes_and_labels_to_image',
    'draw_mpii_people_to_image',
    'frame',
    'CNN2d',
    'images2d',
    'tsne_embedding',
    'draw_weights',
    'W',
]


def read_image(image, path=''):
    

    return imageio.imread(os.path.join(path, image))


def read_images(img_list, path='', n_threads=10, printable=True):
    

    imgs = []
    for idx in range(0, len(img_list), n_threads):
        b_imgs_list = img_list[idx:idx + n_threads]
        b_imgs = tl.prepro.threading_data(b_imgs_list, fn=read_image, path=path)
        
        imgs.extend(b_imgs)
        if printable:
            tl.logging.info('read %d from %s' % (len(imgs), path))
    return imgs


def save_image(image, image_path='_temp.png'):
    

    try:  
        imageio.imwrite(image_path, image)
    except Exception:  
        imageio.imwrite(image_path, image[:, :, 0])


def save_images(images, size, image_path='_temp.png'):
    

    if len(images.shape) == 3:  
        images = images[:, :, :, np.newaxis]

    def merge(images, size):
        h, w = images.shape[1], images.shape[2]
        img = np.zeros((h * size[0], w * size[1], 3), dtype=images.dtype)
        for idx, image in enumerate(images):
            i = idx % size[1]
            j = idx // size[1]
            img[j * h:j * h + h, i * w:i * w + w, :] = image
        return img

    def imsave(images, size, path):
        if np.max(images) <= 1 and (-1 <= np.min(images) < 0):
            images = ((images + 1) * 127.5).astype(np.uint8)
        elif np.max(images) <= 1 and np.min(images) >= 0:
            images = (images * 255).astype(np.uint8)

        return imageio.imwrite(path, merge(images, size))

    if len(images) > size[0] * size[1]:
        raise AssertionError("number of images should be equal or less than size[0] * size[1] {}".format(len(images)))

    return imsave(images, size, image_path)


def draw_boxes_and_labels_to_image(
        image, classes, coords, scores, classes_list, is_center=True, is_rescale=True, save_name=None
):
    

    if len(coords) != len(classes):
        raise AssertionError("number of coordinates and classes are equal")

    if len(scores) > 0 and len(scores) != len(classes):
        raise AssertionError("number of scores and classes are equal")

    
    image = image.copy()

    imh, imw = image.shape[0:2]
    thick = int((imh + imw) // 430)

    for i, _v in enumerate(coords):
        if is_center:
            x, y, x2, y2 = tl.prepro.obj_box_coord_centroid_to_upleft_butright(coords[i])
        else:
            x, y, x2, y2 = coords[i]

        if is_rescale:  
            x, y, x2, y2 = tl.prepro.obj_box_coord_scale_to_pixelunit([x, y, x2, y2], (imh, imw))

        cv2.rectangle(
            image,
            (int(x), int(y)),
            (int(x2), int(y2)),  
            [0, 255, 0],
            thick
        )

        cv2.putText(
            image,
            classes_list[classes[i]] + ((" %.2f" % (scores[i])) if (len(scores) != 0) else " "),
            (int(x), int(y)),  
            0,
            1.5e-3 * imh,  
            [0, 0, 256],  
            int(thick / 2) + 1
        )  

    if save_name is not None:
        
        save_image(image, save_name)
    
    
    return image


def draw_mpii_pose_to_image(image, poses, save_name='image.png'):
    

    
    
    image = image.copy()

    imh, imw = image.shape[0:2]
    thick = int((imh + imw) // 430)
    
    radius = int(thick * 1.5)

    if image.max() < 1:
        image = image * 255

    for people in poses:
        
        joint_pos = people['joint_pos']
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        lines = [
            [(0, 1), [100, 255, 100]],
            [(1, 2), [50, 255, 50]],
            [(2, 6), [0, 255, 0]],  
            [(3, 4), [100, 100, 255]],
            [(4, 5), [50, 50, 255]],
            [(6, 3), [0, 0, 255]],  
            [(6, 7), [255, 255, 100]],
            [(7, 8), [255, 150, 50]],  
            [(8, 9), [255, 200, 100]],  
            [(10, 11), [255, 100, 255]],
            [(11, 12), [255, 50, 255]],
            [(12, 8), [255, 0, 255]],  
            [(8, 13), [0, 255, 255]],
            [(13, 14), [100, 255, 255]],
            [(14, 15), [200, 255, 255]]  
        ]
        for line in lines:
            start, end = line[0]
            if (start in joint_pos) and (end in joint_pos):
                cv2.line(
                    image,
                    (int(joint_pos[start][0]), int(joint_pos[start][1])),
                    (int(joint_pos[end][0]), int(joint_pos[end][1])),  
                    line[1],
                    thick
                )
                
                
        
        for pos in joint_pos.items():
            _, pos_loc = pos  
            pos_loc = (int(pos_loc[0]), int(pos_loc[1]))
            cv2.circle(image, center=pos_loc, radius=radius, color=(200, 200, 200), thickness=-1)
            
            

        
        head_rect = people['head_rect']
        if head_rect:  
            cv2.rectangle(
                image,
                (int(head_rect[0]), int(head_rect[1])),
                (int(head_rect[2]), int(head_rect[3])),  
                [0, 180, 0],
                thick
            )

    if save_name is not None:
        
        save_image(image, save_name)
    return image


draw_mpii_people_to_image = draw_mpii_pose_to_image


def frame(I=None, second=5, saveable=True, name='frame', cmap=None, fig_idx=12836):
    

    import matplotlib.pyplot as plt
    if saveable is False:
        plt.ion()
    plt.figure(fig_idx)  

    if len(I.shape) and I.shape[-1] == 1:  
        I = I[:, :, 0]

    plt.imshow(I, cmap)
    plt.title(name)
    
    

    if saveable:
        plt.savefig(name + '.pdf', format='pdf')
    else:
        plt.draw()
        plt.pause(second)


def CNN2d(CNN=None, second=10, saveable=True, name='cnn', fig_idx=3119362):
    

    import matplotlib.pyplot as plt
    
    
    n_mask = CNN.shape[3]
    n_row = CNN.shape[0]
    n_col = CNN.shape[1]
    n_color = CNN.shape[2]
    row = int(np.sqrt(n_mask))
    col = int(np.ceil(n_mask / row))
    plt.ion()  
    fig = plt.figure(fig_idx)
    count = 1
    for _ir in range(1, row + 1):
        for _ic in range(1, col + 1):
            if count > n_mask:
                break
            fig.add_subplot(col, row, count)
            
            
            
            
            
            if n_color == 1:
                plt.imshow(np.reshape(CNN[:, :, :, count - 1], (n_row, n_col)), cmap='gray', interpolation="nearest")
            elif n_color == 3:
                plt.imshow(
                    np.reshape(CNN[:, :, :, count - 1], (n_row, n_col, n_color)), cmap='gray', interpolation="nearest"
                )
            else:
                raise Exception("Unknown n_color")
            plt.gca().xaxis.set_major_locator(plt.NullLocator())  
            plt.gca().yaxis.set_major_locator(plt.NullLocator())
            count = count + 1
    if saveable:
        plt.savefig(name + '.pdf', format='pdf')
    else:
        plt.draw()
        plt.pause(second)


def images2d(images=None, second=10, saveable=True, name='images', dtype=None, fig_idx=3119362):
    

    import matplotlib.pyplot as plt
    
    
    if dtype:
        images = np.asarray(images, dtype=dtype)
    n_mask = images.shape[0]
    n_row = images.shape[1]
    n_col = images.shape[2]
    n_color = images.shape[3]
    row = int(np.sqrt(n_mask))
    col = int(np.ceil(n_mask / row))
    plt.ion()  
    fig = plt.figure(fig_idx)
    count = 1
    for _ir in range(1, row + 1):
        for _ic in range(1, col + 1):
            if count > n_mask:
                break
            fig.add_subplot(col, row, count)
            
            
            
            
            if n_color == 1:
                plt.imshow(np.reshape(images[count - 1, :, :], (n_row, n_col)), cmap='gray', interpolation="nearest")
                
            elif n_color == 3:
                plt.imshow(images[count - 1, :, :], cmap='gray', interpolation="nearest")
                
            else:
                raise Exception("Unknown n_color")
            plt.gca().xaxis.set_major_locator(plt.NullLocator())  
            plt.gca().yaxis.set_major_locator(plt.NullLocator())
            count = count + 1
    if saveable:
        plt.savefig(name + '.pdf', format='pdf')
    else:
        plt.draw()
        plt.pause(second)


def tsne_embedding(embeddings, reverse_dictionary, plot_only=500, second=5, saveable=False, name='tsne', fig_idx=9862):
    

    import matplotlib.pyplot as plt

    def plot_with_labels(low_dim_embs, labels, figsize=(18, 18), second=5, saveable=True, name='tsne', fig_idx=9862):

        if low_dim_embs.shape[0] < len(labels):
            raise AssertionError("More labels than embeddings")

        if saveable is False:
            plt.ion()
            plt.figure(fig_idx)

        plt.figure(figsize=figsize)  

        for i, label in enumerate(labels):
            x, y = low_dim_embs[i, :]
            plt.scatter(x, y)
            plt.annotate(label, xy=(x, y), xytext=(5, 2), textcoords='offset points', ha='right', va='bottom')

        if saveable:
            plt.savefig(name + '.pdf', format='pdf')
        else:
            plt.draw()
            plt.pause(second)

    try:
        from sklearn.manifold import TSNE
        from six.moves import xrange

        tsne = TSNE(perplexity=30, n_components=2, init='pca', n_iter=5000)
        
        low_dim_embs = tsne.fit_transform(embeddings[:plot_only, :])
        labels = [reverse_dictionary[i] for i in xrange(plot_only)]
        plot_with_labels(low_dim_embs, labels, second=second, saveable=saveable, name=name, fig_idx=fig_idx)

    except ImportError:
        _err = "Please install sklearn and matplotlib to visualize embeddings."
        tl.logging.error(_err)
        raise ImportError(_err)


def draw_weights(W=None, second=10, saveable=True, shape=None, name='mnist', fig_idx=2396512):
    

    if shape is None:
        shape = [28, 28]

    import matplotlib.pyplot as plt
    if saveable is False:
        plt.ion()
    fig = plt.figure(fig_idx)  
    n_units = W.shape[1]

    num_r = int(np.sqrt(n_units))  
    num_c = int(np.ceil(n_units / num_r))
    count = int(1)
    for _row in range(1, num_r + 1):
        for _col in range(1, num_c + 1):
            if count > n_units:
                break
            fig.add_subplot(num_r, num_c, count)
            
            
            
            feature = W[:, count - 1] / np.sqrt((W[:, count - 1]**2).sum())
            
            
            
            
            
            
            
            plt.imshow(
                np.reshape(feature, (shape[0], shape[1])), cmap='gray', interpolation="nearest"
            )  
            
            
            
            plt.gca().xaxis.set_major_locator(plt.NullLocator())  
            plt.gca().yaxis.set_major_locator(plt.NullLocator())
            count = count + 1
    if saveable:
        plt.savefig(name + '.pdf', format='pdf')
    else:
        plt.draw()
        plt.pause(second)


W = draw_weights
