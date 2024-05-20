import ResponsiveAppBar from "./AppToolBar";
import CustomGrid from "./CustomGrid";
import * as React from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import {
    Card, CardContent, CardMedia,
    Chip,
    Dialog, DialogActions,
    DialogContent,
    DialogTitle,
    Paper,
    TextField
} from "@mui/material";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";



function ShowOne({ id }) {
    const [open, setOpen] = React.useState(false);
    const [object, setObject] = React.useState({});
    const [editedAnime, setEditedAnime] = React.useState({});
    const navigate = useNavigate();

    const handleDeleteGenre = (genreToRemove) => {
        setEditedAnime((prevEditedAnime) => ({
            ...prevEditedAnime,
            genres: prevEditedAnime.genres.filter(genre => genre.name !== genreToRemove.name)
        }));
    };

    const handleClickDelete = () => {
        axios.delete(`anime/` + object.id)
            .then(response => setObject(response.data))
            .catch(error => console.error('Error deleting the anime:', error));
        navigate("/animes");
    }

    const handleClickOpen = () => {
        setEditedAnime(object);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSave = () => {
        setOpen(false);
        console.log('Edited Anime being sent:', editedAnime);

        axios.put(`https://manyame.up.railway.app/anime`, editedAnime, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => setObject(response.data))
            .catch(error => console.error('Error updating the anime:', error));
    };

    const handleChange = (event) => {
        const { name, value } = event.target;
        setEditedAnime({ ...editedAnime, [name]: value });
    };

    React.useEffect(() => {
        axios.get(`https://manyame.up.railway.app/anime/${id}`).then((response) => {
            setObject(response.data);
            setEditedAnime(response.data);
        });
    }, [id]);

    return (
        <Container>
            <Paper elevation={3} sx={{ padding: 2, marginTop: 4 }}>
                <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                    <img
                        src={object?.image}
                        alt={object?.name}
                        style={{ width: '100%', maxWidth: '300px', height: 'auto', marginBottom: '16px' }}
                    />
                    <Box sx={{ marginLeft: '15px', display: 'flex', flexDirection: 'column', alignItems: 'left' }}>
                        <Typography variant="h3" component="h1">
                            {object?.name}
                        </Typography>
                        <Typography variant="h3" component="h1">
                            {object?.russian}
                        </Typography>
                        <Typography variant="h5" component="h1">
                            Статус: {object?.status}
                        </Typography>
                        <Typography variant="h5" component="h1">
                            Рейтинг: {object?.score}
                        </Typography>

                        <Typography variant="h6" component="h2" color="text.secondary">
                            Запущен: {new Date(object?.aired_on).toLocaleDateString()}
                        </Typography>
                        <Typography variant="h6" component="h2" color="text.secondary">
                            Количество эпизодов: {object?.episodes}
                        </Typography>
                        <Box sx={{ display: 'flex', justifyContent: 'left', flexWrap: 'wrap', gap: 1, marginTop: 1 }}>
                            {object?.genres?.map((genre, index) => (
                                <Chip label={genre.russian} key={index} color="primary" />
                            ))}
                        </Box>
                        <Card sx={{maxHeight: '400px', maxWidth: '300px',  flexDirection: 'column', alignItems: 'center', marginTop: 1 }}>
                            <CardMedia
                                component="img"
                                image={"https://shikimori.one"+ object?.studio?.image}
                                sx = {{
                                    maxHeight: '400px',
                                    maxWidth: '300px'
                                }}
                            />
                            <CardContent>
                                <Typography variant="h7" component="div">
                                    Студия {object?.studio?.name}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                </Box>
                <Typography variant="body1" component="p" sx={{ marginTop: '20px', textAlign: 'justify' }}>
                    {object?.description}
                </Typography>
                <Button onClick={handleClickOpen} color="primary">
                    Редактировать
                </Button>
                <Button onClick={handleClickDelete} color="primary">
                    Удалить
                </Button>
            </Paper>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Редактировать аниме</DialogTitle>
                <DialogContent>
                    <TextField
                        margin="dense"
                        name="name"
                        type="text"
                        fullWidth
                        value={editedAnime.name || ''}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="russian"
                        type="text"
                        fullWidth
                        value={editedAnime.russian || ''}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="status"
                        type="text"
                        fullWidth
                        value={editedAnime.status || ''}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="score"
                        type="number"
                        fullWidth
                        value={editedAnime.score || ''}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="aired_on"
                        label="Aired On"
                        fullWidth
                        value={editedAnime.aired_on || ''}
                        onChange={handleChange}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                    <TextField
                        margin="dense"
                        name="episodes"
                        type="number"
                        fullWidth
                        value={editedAnime.episodes}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="description"
                        type="text"
                        multiline
                        rows={4}
                        fullWidth
                        value={editedAnime.description || ''}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="dense"
                        name="image"
                        type="text"
                        fullWidth
                        value={editedAnime.image}
                        onChange={handleChange}
                    />
                    <Box sx={{ display: 'flex', justifyContent: 'center', flexWrap: 'wrap', gap: 1, marginTop: 1 }}>
                        {editedAnime?.genres?.map((genre, index) => (
                            <Chip label={genre.russian} key={index} color="primary" onDelete={event => handleDeleteGenre(genre)}/>
                        ))}
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Отмена
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        Сохранить
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default function Anime() {
    let location = useLocation();
    const id = new URLSearchParams(location.search).get("id");
    const search = new URLSearchParams(location.search).get("search");
    console.log(id);
    if (id != null) {
        return (
            <div>
                <ResponsiveAppBar />
                <ShowOne id={String(id)} />
            </div>
        );
    } else if(search != null) {
        return (
            <div>
                <ResponsiveAppBar />
            </div>
        )
    }
    else {
        return (
            <div>
                <ResponsiveAppBar />
                <CustomGrid endpoint='/anime' />
            </div>
        );
    }
}
