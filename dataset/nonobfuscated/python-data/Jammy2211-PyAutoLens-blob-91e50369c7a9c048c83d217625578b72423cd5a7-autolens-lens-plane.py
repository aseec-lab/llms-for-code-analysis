import numpy as np
from astropy import constants
from astropy import cosmology as cosmo
from functools import wraps

from autolens import exc
from autolens.data.array import grids
from autolens.data.array import scaled_array
from autolens.lens.util import lens_util
from autolens.model import dimensions as dim
from autolens.model import cosmology_util
from autolens.model.galaxy.util import galaxy_util


class AbstractPlane(object):

    def __init__(self, redshift, galaxies, cosmology=cosmo.Planck15):
        


        self.redshift = redshift
        self.galaxies = galaxies
        self.cosmology = cosmology

    @property
    def galaxy_redshifts(self):
        return [galaxy.redshift for galaxy in self.galaxies]

    @property
    def arcsec_per_kpc(self):
        return cosmology_util.arcsec_per_kpc_from_redshift_and_cosmology(redshift=self.redshift,
                                                                         cosmology=self.cosmology)

    @property
    def kpc_per_arcsec(self):
        return 1.0 / self.arcsec_per_kpc

    def angular_diameter_distance_to_earth_in_units(self, unit_length='arcsec'):
        return cosmology_util.angular_diameter_distance_to_earth_from_redshift_and_cosmology(
            redshift=self.redshift, cosmology=self.cosmology, unit_length=unit_length)

    def cosmic_average_density_in_units(self, unit_length='arcsec', unit_mass='solMass'):
        return cosmology_util.cosmic_average_density_from_redshift_and_cosmology(
            redshift=self.redshift, cosmology=self.cosmology, unit_length=unit_length, unit_mass=unit_mass)

    @property
    def has_light_profile(self):
        return any(list(map(lambda galaxy: galaxy.has_light_profile, self.galaxies)))

    @property
    def has_mass_profile(self):
        return any(list(map(lambda galaxy: galaxy.has_mass_profile, self.galaxies)))

    @property
    def has_pixelization(self):
        return any(list(map(lambda galaxy: galaxy.has_pixelization, self.galaxies)))

    @property
    def has_regularization(self):
        return any(list(map(lambda galaxy: galaxy.has_regularization, self.galaxies)))

    @property
    def regularization(self):

        galaxies_with_regularization = list(filter(lambda galaxy: galaxy.has_regularization, self.galaxies))

        if len(galaxies_with_regularization) == 0:
            return None
        if len(galaxies_with_regularization) == 1:
            return galaxies_with_regularization[0].regularization
        elif len(galaxies_with_regularization) > 1:
            raise exc.PixelizationException('The number of galaxies with regularizations in one plane is above 1')

    @property
    def centres_of_galaxy_mass_profiles(self):

        if self.has_mass_profile:

            galaxies_with_mass_profiles = [galaxy for galaxy in self.galaxies if galaxy.has_mass_profile]

            mass_profile_centres = [[] for i in range(len(galaxies_with_mass_profiles))]

            for galaxy_index, galaxy in enumerate(galaxies_with_mass_profiles):
                mass_profile_centres[galaxy_index] = [profile.centre for profile in galaxy.mass_profiles]
            return mass_profile_centres

        else:

            return None

    @property
    def axis_ratios_of_galaxy_mass_profiles(self):

        if self.has_mass_profile:

            galaxies_with_mass_profiles = [galaxy for galaxy in self.galaxies if galaxy.has_mass_profile]

            mass_profile_axis_ratios = [[] for i in range(len(galaxies_with_mass_profiles))]

            for galaxy_index, galaxy in enumerate(galaxies_with_mass_profiles):
                mass_profile_axis_ratios[galaxy_index] = [profile.axis_ratio for profile in galaxy.mass_profiles]
            return mass_profile_axis_ratios

        else:

            return None
        
    @property
    def phis_of_galaxy_mass_profiles(self):

        if self.has_mass_profile:

            galaxies_with_mass_profiles = [galaxy for galaxy in self.galaxies if galaxy.has_mass_profile]

            mass_profile_phis = [[] for i in range(len(galaxies_with_mass_profiles))]

            for galaxy_index, galaxy in enumerate(galaxies_with_mass_profiles):
                mass_profile_phis[galaxy_index] = [profile.phi for profile in galaxy.mass_profiles]
            return mass_profile_phis

        else:

            return None

    def luminosities_of_galaxies_within_circles_in_units(self, radius : dim.Length, unit_luminosity='eps', exposure_time=None):
        

        return list(map(lambda galaxy: galaxy.luminosity_within_circle_in_units(
            radius=radius, unit_luminosity=unit_luminosity, kpc_per_arcsec=self.kpc_per_arcsec,
            exposure_time=exposure_time),
                        self.galaxies))

    def luminosities_of_galaxies_within_ellipses_in_units(self, major_axis : dim.Length, unit_luminosity='eps',
                                                          exposure_time=None):
        

        return list(map(lambda galaxy: galaxy.luminosity_within_ellipse_in_units(
            major_axis=major_axis, unit_luminosity=unit_luminosity, kpc_per_arcsec=self.kpc_per_arcsec,
            exposure_time=exposure_time),
                        self.galaxies))

    def masses_of_galaxies_within_circles_in_units(self, radius : dim.Length, unit_mass='angular',
                                                   critical_surface_density=None):
        

        return list(map(lambda galaxy: galaxy.mass_within_circle_in_units(
                        radius=radius, unit_mass=unit_mass, kpc_per_arcsec=self.kpc_per_arcsec,
                        critical_surface_density=critical_surface_density),
                        self.galaxies))

    def masses_of_galaxies_within_ellipses_in_units(self, major_axis : dim.Length, unit_mass='angular',
                                                    critical_surface_density=None):
        

        return list(map(lambda galaxy: galaxy.mass_within_ellipse_in_units(
                        major_axis=major_axis, unit_mass=unit_mass, kpc_per_arcsec=self.kpc_per_arcsec,
                        critical_surface_density=critical_surface_density),
                        self.galaxies))

    def einstein_radius_in_units(self, unit_length='arcsec', kpc_per_arcsec=None):

        if self.has_mass_profile:
            return sum(filter(None,
                   list(map(lambda galaxy: galaxy.einstein_radius_in_units(
                       unit_length=unit_length, kpc_per_arcsec=kpc_per_arcsec),
                            self.galaxies))))

    def einstein_mass_in_units(self, unit_mass='angular', critical_surface_density=None):

        if self.has_mass_profile:
            return sum(filter(None,
                   list(map(lambda galaxy: galaxy.einstein_mass_in_units(
                       unit_mass=unit_mass, critical_surface_density=critical_surface_density),
                            self.galaxies))))

class AbstractGriddedPlane(AbstractPlane):

    def __init__(self, redshift, galaxies, grid_stack, border, compute_deflections, cosmology=cosmo.Planck15):
        


        super(AbstractGriddedPlane, self).__init__(redshift=redshift, galaxies=galaxies, cosmology=cosmology)

        self.grid_stack = grid_stack
        self.border = border

        if compute_deflections:

            def calculate_deflections(grid):

                if galaxies:
                    return sum(map(lambda galaxy: galaxy.deflections_from_grid(grid), galaxies))
                else:
                    return np.full((grid.shape[0], 2), 0.0)

            self.deflection_stack = self.grid_stack.apply_function(calculate_deflections)

        else:

            self.deflection_stack = None

    def trace_grid_stack_to_next_plane(self):
        


        def minus(grid, deflections):
            return grid - deflections

        return self.grid_stack.map_function(minus, self.deflection_stack)

    @property
    def image_plane_image(self):
        return self.grid_stack.scaled_array_2d_from_array_1d(self.image_plane_image_1d)

    @property
    def image_plane_image_for_simulation(self):
        if not self.has_padded_grid_stack:
            raise exc.RayTracingException(
                'To retrieve an image plane image for the simulation, the grid_stacks in the tracer_normal'
                'must be padded grid_stacks')
        return self.grid_stack.regular.map_to_2d_keep_padded(padded_array_1d=self.image_plane_image_1d)

    @property
    def image_plane_image_1d(self):
        return galaxy_util.intensities_of_galaxies_from_grid(grid=self.grid_stack.sub, galaxies=self.galaxies)

    @property
    def image_plane_image_1d_of_galaxies(self):
        return list(map(self.image_plane_image_1d_of_galaxy, self.galaxies))

    def image_plane_image_1d_of_galaxy(self, galaxy):
        return galaxy_util.intensities_of_galaxies_from_grid(grid=self.grid_stack.sub, galaxies=[galaxy])

    @property
    def image_plane_blurring_image_1d(self):
        return galaxy_util.intensities_of_galaxies_from_grid(grid=self.grid_stack.blurring, galaxies=self.galaxies)

    @property
    def convergence(self):
        convergence_1d = galaxy_util.convergence_of_galaxies_from_grid(
            grid=self.grid_stack.sub.unlensed_grid, galaxies=self.galaxies)
        return self.grid_stack.scaled_array_2d_from_array_1d(array_1d=convergence_1d)

    @property
    def potential(self):
        potential_1d = galaxy_util.potential_of_galaxies_from_grid(grid=self.grid_stack.sub.unlensed_grid,
                                                                   galaxies=self.galaxies)
        return self.grid_stack.scaled_array_2d_from_array_1d(array_1d=potential_1d)

    @property
    def deflections_y(self):
        return self.grid_stack.scaled_array_2d_from_array_1d(self.deflections_1d[:, 0])

    @property
    def deflections_x(self):
        return self.grid_stack.scaled_array_2d_from_array_1d(self.deflections_1d[:, 1])

    @property
    def deflections_1d(self):
        return galaxy_util.deflections_of_galaxies_from_grid(grid=self.grid_stack.sub.unlensed_grid,
                                                             galaxies=self.galaxies)

    @property
    def has_padded_grid_stack(self):
        return isinstance(self.grid_stack.regular, grids.PaddedRegularGrid)

    @property
    def plane_image(self):
        return lens_util.plane_image_of_galaxies_from_grid(shape=self.grid_stack.regular.mask.shape,
                                                           grid=self.grid_stack.regular,
                                                           galaxies=self.galaxies)

    @property
    def mapper(self):

        galaxies_with_pixelization = list(filter(lambda galaxy: galaxy.has_pixelization, self.galaxies))

        if len(galaxies_with_pixelization) == 0:
            return None
        if len(galaxies_with_pixelization) == 1:
            pixelization = galaxies_with_pixelization[0].pixelization
            return pixelization.mapper_from_grid_stack_and_border(grid_stack=self.grid_stack, border=self.border)
        elif len(galaxies_with_pixelization) > 1:
            raise exc.PixelizationException('The number of galaxies with pixelizations in one plane is above 1')

    @property
    def yticks(self):
        

        return np.linspace(np.amin(self.grid_stack.regular[:, 0]), np.amax(self.grid_stack.regular[:, 0]), 4)

    @property
    def xticks(self):
        

        return np.linspace(np.amin(self.grid_stack.regular[:, 1]), np.amax(self.grid_stack.regular[:, 1]), 4)


class Plane(AbstractGriddedPlane):

    def __init__(self, galaxies, grid_stack, border=None, compute_deflections=True, cosmology=cosmo.Planck15):
        


        if not galaxies:
            raise exc.RayTracingException('An empty list of galaxies was supplied to Plane')

        galaxy_redshifts = [galaxy.redshift for galaxy in galaxies]

        if any([redshift is not None for redshift in galaxy_redshifts]):
            if not all([galaxies[0].redshift == galaxy.redshift for galaxy in galaxies]):
                raise exc.RayTracingException('The galaxies supplied to A Plane have different redshifts or one galaxy '
                                              'does not have a redshift.')

        super(Plane, self).__init__(redshift=galaxies[0].redshift, galaxies=galaxies, grid_stack=grid_stack,
                                    border=border, compute_deflections=compute_deflections, cosmology=cosmology)

    def unmasked_blurred_image_of_galaxies_from_psf(self, padded_grid_stack, psf):
        

        return [padded_grid_stack.unmasked_blurred_image_from_psf_and_unmasked_image(
            psf, image) if not galaxy.has_pixelization else None for galaxy, image in
                zip(self.galaxies, self.image_plane_image_1d_of_galaxies)]

    def unmasked_blurred_image_of_galaxy_with_grid_stack_psf(self, galaxy, padded_grid_stack, psf):
        return padded_grid_stack.unmasked_blurred_image_from_psf_and_unmasked_image(
            psf,
            self.image_plane_image_1d_of_galaxy(
                galaxy))


class PlaneSlice(AbstractGriddedPlane):

    def __init__(self, galaxies, grid_stack, redshift, border=None, compute_deflections=True, cosmology=cosmo.Planck15):
        


        super(PlaneSlice, self).__init__(redshift=redshift, galaxies=galaxies, grid_stack=grid_stack, border=border,
                                         compute_deflections=compute_deflections, cosmology=cosmology)


class PlanePositions(object):

    def __init__(self, redshift, galaxies, positions, compute_deflections=True, cosmology=None):
        


        self.redshift = redshift
        self.galaxies = galaxies
        self.positions = positions

        if compute_deflections:
            def calculate_deflections(pos):
                return sum(map(lambda galaxy: galaxy.deflections_from_grid(pos), galaxies))

            self.deflections = list(map(lambda pos: calculate_deflections(pos), self.positions))

        self.cosmology = cosmology

    def trace_to_next_plane(self):
        

        return list(map(lambda positions, deflections: np.subtract(positions, deflections),
                        self.positions, self.deflections))


class PlaneImage(scaled_array.ScaledRectangularPixelArray):

    def __init__(self, array, pixel_scales, grid, origin=(0.0, 0.0)):
        self.grid = grid
        super(PlaneImage, self).__init__(array=array, pixel_scales=pixel_scales, origin=origin)
